import {Component, ElementRef, HostListener, OnInit, ViewChild} from '@angular/core';
import {BackendService} from './backend.service';
import {throttleTime} from 'rxjs/operators';
import {Player} from './model/Player.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'web-client';

  @ViewChild('canvasElement') canvasRef: ElementRef;
  private context: CanvasRenderingContext2D;
  private hasFullScreen = false;
  private canvas: HTMLCanvasElement;
  private lastState: { players: Player[] };
  private targetNickname;
  private currentPlayerNickname = 'Player_' + (Math.random() * 1000).toFixed(0);
  private translate = { x: 100, y: 100 };

  constructor(private backend: BackendService) {
  }

  ngOnInit(): void {
    this.canvas = this.canvasRef.nativeElement as HTMLCanvasElement;
    this.context = this.canvas.getContext('2d');
    this.context.imageSmoothingEnabled = false;
    this.canvas.addEventListener('click', ev => this.canvasClick(ev));
    this.resize();
    this.redraw();
    this.backend.connect(this.currentPlayerNickname);
    this.backend.getMessage()
      .pipe(throttleTime(30))
      .subscribe(state => {
        this.lastState = state;
        const player = state.players.find(p => p.nickname === this.currentPlayerNickname);
        this.translate.x = Math.round(this.canvas.width / 2) - player.position.x;
        this.translate.y = Math.round(this.canvas.height / 2) - player.position.y;
        this.redraw(state);
      });
  }

  @HostListener('window:resize', ['$event'])
  private resize() {
    this.canvas.setAttribute('width', String(window.innerWidth));
    this.canvas.setAttribute('height', String(window.innerHeight));
    this.redraw();
  }

  private canvasClick(event: MouseEvent) {
    const offsetX = event.offsetX - this.translate.x;
    const offsetY = event.offsetY - this.translate.y;
    const findPlayer = this.lastState.players
      .find(player => player.position.x - 10 <= offsetX
        && player.position.x + 10 >= offsetX
        && player.position.y - 10 <= offsetY
        && player.position.y + 10 >= offsetY
        && player.nickname !== this.currentPlayerNickname);
    this.targetNickname = findPlayer ? findPlayer.nickname : null;
    if (this.targetNickname) {
      this.backend.sendAttack(this.targetNickname);
    } else {
      this.backend.sendMove({ x: offsetX, y: offsetY });
    }
    console.log('Click', event);
  }

  private redraw(state?: {players: Player[]}) {
    this.context.fillStyle = '#608038';
    this.context.fillRect(0, 0, this.context.canvas.width, this.context.canvas.height);
    this.context.translate(this.translate.x, this.translate.y);
    if (state) {
      state.players.forEach(player => {
        const oldStyle = this.context.fillStyle;
        const oldStroke = this.context.strokeStyle;

        // Draw player body
        if (player.nickname === this.targetNickname) {
          this.context.fillStyle = 'red';
        } else {
          this.context.fillStyle = 'skyblue';
        }
        this.context.fillRect(player.position.x - 10, player.position.y - 10, 20, 20);

        // Draw player nickname
        const nicknameWidth = this.context.measureText(player.nickname).width;
        this.context.strokeText(player.nickname, player.position.x - nicknameWidth / 2, player.position.y - 20);

        // Draw health bar
        this.context.strokeStyle = '#7B241C';
        this.context.beginPath();
        this.context.rect(player.position.x - 20, player.position.y - 16, 40, 4);
        this.context.stroke();

        this.context.fillStyle = '#EC7063';
        const maxHealthWidth = 38;
        this.context.fillRect(
          player.position.x - 19,
          player.position.y - 15,
          (player.health / player.maxHealth) * maxHealthWidth,
          2
        );

        // Draw target
        if (player.nickname === this.currentPlayerNickname
            && player.target
            && (player.target.x !== player.position.x
            || player.target.y !== player.position.y)) {
          this.context.beginPath();
          this.context.ellipse(player.target.x, player.target.y, 6, 6, 0, 0, 180);
          this.context.stroke();
        }

        this.context.fillStyle = oldStyle;
        this.context.strokeStyle = oldStroke;
      });
    }
    this.context.translate(-this.translate.x, -this.translate.y);
  }

}

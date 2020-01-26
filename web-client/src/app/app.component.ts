import {Component, ElementRef, HostListener, OnInit, ViewChild} from '@angular/core';
import {BackendService} from './backend.service';
import {throttleTime} from 'rxjs/operators';

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

  constructor(private backend: BackendService) {
  }

  ngOnInit(): void {
    this.canvas = this.canvasRef.nativeElement as HTMLCanvasElement;
    this.context = this.canvas.getContext('2d');
    this.context.imageSmoothingEnabled = false;
    this.canvas.addEventListener('click', ev => this.canvasClick(ev));
    this.resize();
    this.redraw();
    this.backend.connect('Player' + (Math.random() * 100).toFixed(0));
    this.backend.getMessage()
      .pipe(throttleTime(30))
      .subscribe(state => {
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
    this.backend.sendMove({ x: event.offsetX, y: event.offsetY });
    console.log('Click', event);
  }

  private redraw(state?: {players: [{nickname: string, position: {x, y}, target: {x, y}, speed: number}]}) {
    this.context.fillStyle = '#608038';
    this.context.fillRect(0, 0, this.context.canvas.width, this.context.canvas.height);
    if (state) {
      state.players.forEach(value => {
        const oldStyle = this.context.fillStyle;
        this.context.fillStyle = 'red';
        this.context.fillRect(value.position.x - 10, value.position.y - 10, 20, 20);
        const nicknameWidth = this.context.measureText(value.nickname).width;
        this.context.strokeText(value.nickname, value.position.x - nicknameWidth / 2, value.position.y - 15);
        this.context.fillStyle = oldStyle;
      });
    }
  }
}

import {Component, OnInit} from '@angular/core';
import {BackendService} from './backend.service';
import {throttleTime} from 'rxjs/operators';
import {Player} from './model/Player.model';
import Point = PIXI.Point;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  private lastState: { players: Player[] };
  public state: { players: Player[] };
  private currentPlayer: Player;
  private currentPlayerNickname = 'Player_' + (Math.random() * 1000).toFixed(0);

  constructor(private backend: BackendService) {
  }

  ngOnInit(): void {
    this.backend.connect(this.currentPlayerNickname);
    this.backend.getMessage()
      .pipe(throttleTime(30))
      .subscribe(state => {
        this.lastState = this.state;
        this.state = state;
        this.currentPlayer = state.players.find(p => p.nickname === this.currentPlayerNickname);
      });
  }

  private canvasClick(point: Point) {
    const offsetX = point.x;
    const offsetY = point.y;
    this.backend.sendMove({ x: offsetX, y: offsetY });
  }
}

import { Injectable } from '@angular/core';
import {WebSocketSubject} from 'rxjs/webSocket';
import {Observable, Subject} from 'rxjs';
import {Player} from './model/Player.model';

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  private websocket$: WebSocketSubject<any>;
  private message$ = new Subject();

  constructor() {
  }

  public connect(nickname: string) {
    this.websocket$ = new WebSocketSubject<string>({
      url: `ws://${document.location.hostname}:8080/player?name=${nickname}`,
    });
    this.websocket$.subscribe(value => {
      this.message$.next(value);
    });
  }

  public getMessage(): Observable<{players: Player[]}> {
    return this.message$.asObservable() as any;
  }

  public sendMove(target: {x: number, y: number}) {
    this.websocket$.next({
      group: 'PLAYER',
      action: 'MOVE',
      content: {
        x: Math.round(target.x),
        y: Math.round(target.y),
      },
    });
  }

  sendAttack(targetNickname: string) {
    this.websocket$.next({
      group: 'PLAYER',
      action: 'ATTACK',
      content: {
        target: targetNickname,
      },
    });
  }
}

import { Injectable } from '@angular/core';
import {WebSocketSubject} from 'rxjs/webSocket';
import {Observable, Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  private websocket$: WebSocketSubject<string>;
  private message$ = new Subject();
  public socket = new Subject();


  constructor() {
  }

  public connect(nickname: string) {
    this.websocket$ = new WebSocketSubject<string>({
      url: `ws://192.168.0.101:8080/player?name=${nickname}`,
    });
    this.websocket$.subscribe(value => {
      this.message$.next(value);
    });
  }

  public getMessage(): Observable<{players: [{nickname: string, position: {x, y}, target: {x, y}, speed: number}]}> {
    return this.message$.asObservable() as any;
  }

  public sendMove(target: {x: number, y: number}) {
    this.websocket$.next({
      group: 'PLAYER',
      action: 'MOVE',
      content: target,
    } as any);
  }
}

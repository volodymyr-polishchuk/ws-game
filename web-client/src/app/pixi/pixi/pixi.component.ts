import {Component, ElementRef, EventEmitter, HostListener, Input, NgZone, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {Application, Container, Graphics, Sprite, Text} from 'pixi.js';
import {Player} from '../../model/Player.model';
import {Subject} from 'rxjs';
import {ApplicationOptions} from './applicationOptions';
import Point = PIXI.Point;

@Component({
  selector: 'app-pixi',
  templateUrl: './pixi.component.html',
  styleUrls: ['./pixi.component.css']
})
export class PixiComponent implements OnInit, OnDestroy {

  public app: Application;

  @Input()
  public devicePixelRatio = window.devicePixelRatio || 1;

  @Input()
  public applicationOptions: ApplicationOptions = {};

  @Input()
  public state: { players: Player[] } = { players: [] };

  @Input()
  public mainPlayer: Player;

  @Output()
  public playerClick = new EventEmitter<Point>();

  private playerSelect$ = new Subject();

  private sprites = new Map();

  @ViewChild('element')
  private elementRef: ElementRef;

  constructor(private ngZone: NgZone) {}

  init() {
    this.ngZone.runOutsideAngular(() => {
      this.app = new Application(this.applicationOptions);
      const map = Sprite.from('assets/map/map.png');
      map.scale.set(2, 2);
      map.position.set(-400, -200);
      map.buttonMode = true;
      map.interactive = true;
      map.on('pointerdown', this.mapClickHandler.bind(this));
      this.app.stage.addChild(map);
      this.app.stage.position.set(
        this.app.renderer.width / 2,
        this.app.renderer.height / 2
      );
      this.app.stage.on('pointerdown', (event) => {
        console.log(event);
      });
      this.app.ticker.add(params => {
        this.updateToState(params);
      });
    });
    this.elementRef.nativeElement.appendChild(this.app.view);
    this.resize();
    this.playerSelect$.subscribe(value => {
      console.log(value);
    });
  }

  mapClickHandler(ev) {
    const value = ev.data.global;
    value.x += this.app.stage.pivot.x - this.app.view.width / 2;
    value.y += this.app.stage.pivot.y - this.app.view.height / 2;
    console.log(value);
    this.playerClick.emit(value);
  }

  ngOnInit(): void {
    this.init();
  }

  @HostListener('window:resize')
  public resize() {
    const width = this.elementRef.nativeElement.offsetWidth;
    const height = this.elementRef.nativeElement.offsetHeight;
    this.app.renderer.resize(width * this.devicePixelRatio, height * this.devicePixelRatio);
    this.app.renderer.backgroundColor = 0xffff00;
    this.app.view.style.width = width + 'px';
    this.app.view.style.height = height + 'px';
    this.app.stage.position.set(
      this.app.renderer.width / 2,
      this.app.renderer.height / 2
    );
  }

  destroy() {
    this.app.destroy();
  }

  ngOnDestroy(): void {
    this.destroy();
  }


  private updateToState(params: any) {
    if (!this.state || !this.state.players) {
      return;
    }
    this.state.players.forEach(player => {
      if ( ! this.sprites.has(player.nickname)) {
        this.createPlayer(player);
      }

      const sprite = this.sprites.get(player.nickname) as Sprite;
      this.movePlayer(player, sprite);
      sprite.removeChildAt(2);
      sprite.addChild(this.getHealthBar(player.health, player.maxHealth));
      this.turnPlayerToDirection(player, sprite);
      if (player === this.mainPlayer) {
        this.moveCameraToMainPlayer(sprite);
      }
    });

    this.removeDisconnectedPlayers();

  }

  private removeDisconnectedPlayers() {
    this.sprites.forEach((value, key, map) => {
      if (!this.state.players.find(player => player.nickname === key)) {
        map.delete(key);
        this.app.stage.removeChild(value);
        value.destroy();
        console.log('Delete', value, key, map);
      }
    });
  }

  private movePlayer(player, sprite) {
    const speedPerFrame = player.speed / 60;
    if (player.target) {
      sprite.position = this.moveToward(sprite.position, player.target, speedPerFrame);
    }
  }

  private turnPlayerToDirection(player, sprite: Sprite) {
    if (player.target && player.position.x > player.target.x) {
      sprite.children[1].scale.x = Math.abs(sprite.children[0].scale.x);
    } else {
      sprite.children[1].scale.x = Math.abs(sprite.children[0].scale.x) * -1;
    }
  }

  private moveCameraToMainPlayer(sprite) {
    this.app.stage.pivot.set(
      sprite.position.x,
      sprite.position.y,
    );
  }

  private createPlayer(player: Player) {
    const container = new Container();
    container.buttonMode = true;
    container.interactive = true;
    container.name = player.nickname;
    container.on('pointerdown', event => {
      this.playerSelect$.next(event.target.name);
    });
    const sp = Sprite.from('assets/sprites/warior.png');
    sp.pivot.set(player.bounce.width / 2, player.bounce.height);
    container.position.set(player.bounce.center.x, player.bounce.center.y);
    this.sprites.set(player.nickname, container);
    const nickname = new Text(player.nickname);
    nickname.position.x = -60;
    nickname.position.y = -120;
    const healthBar = this.getHealthBar(player.health, player.maxHealth);
    container.addChild(nickname);
    container.addChild(sp);
    container.addChild(healthBar);
    this.app.stage.addChild(container);
  }

  private getHealthBar(health, maxHealth) {
    const healthBar = new Graphics();
    healthBar.beginFill(0x000000);
    healthBar.drawRect(0, 0, 120, 10);
    healthBar.endFill();
    healthBar.beginFill(0xFF0000);
    healthBar.drawRect(2, 2, 116 * (health / maxHealth), 6);
    healthBar.endFill();
    healthBar.position.set(-60, -85);
    return healthBar;
  }

  private moveToward(position, target, speed) {
    if (Math.abs(position.x - target.x) <= speed
      && Math.abs(position.y - target.y) <= speed) {
      return target;
    }

    const point = {x: 0, y: 0};

    if (Math.abs(position.x - target.x) <= speed) {
      point.x = target.x;
    } else {
      const signX = Math.sign( target.x - position.x);
      point.x = position.x + signX * speed;
    }

    if (Math.abs(position.y - target.y) <= speed) {
      point.y = target.y;
    } else {
      const signY = Math.sign( target.y - position.y);
      point.y = position.y + signY * speed;
    }

    return point;
  }
}

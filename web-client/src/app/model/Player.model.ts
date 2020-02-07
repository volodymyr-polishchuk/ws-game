export interface Player {
  nickname: string;
  position: {x, y};
  target: {x, y};
  speed: number;
  health: number;
  maxHealth: number;
  bounce: {
    height: number;
    width: number;
    center: {x, y};
  };
}

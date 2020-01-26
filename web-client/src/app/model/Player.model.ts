export interface Player {
  nickname: string;
  position: {x, y};
  target: {x, y};
  speed: number;
  health: number;
  maxHealth: number;
}

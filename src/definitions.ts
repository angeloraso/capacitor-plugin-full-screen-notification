export interface FullScreenNotificationPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}

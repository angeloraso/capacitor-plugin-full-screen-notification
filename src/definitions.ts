export interface FullScreenNotificationPlugin {
  show(data: {name: string, number: string, thereIsACallInProgress: boolean}): Promise<{ data: string }>;
  hide(): Promise<{ data: 'success' | 'error' }>;
}

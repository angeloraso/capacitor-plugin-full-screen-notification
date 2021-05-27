// eslint-disable-next-line import/order
import { WebPlugin } from '@capacitor/core';
import type { FullScreenNotificationPlugin } from './definitions';

export class FullScreenNotificationWeb extends WebPlugin implements FullScreenNotificationPlugin {
  async show(terminal: {name: string, number: string}): Promise<{ data: string }> {
    return { data: terminal.name };
  }

  async hide(): Promise<{ data: 'success' | 'error' }> {
    return {data: 'success'};
  }
}

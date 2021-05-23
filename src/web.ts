// eslint-disable-next-line import/order
import { WebPlugin } from '@capacitor/core';
import type { FullScreenNotificationPlugin } from './definitions';

export class FullScreenNotificationWeb extends WebPlugin implements FullScreenNotificationPlugin {
  async show(): Promise<{ data: string }> {
    return { data: 'show' };
  }

  async hide(): Promise<{ data: string }> {
    return { data: 'hide' };
  }
}

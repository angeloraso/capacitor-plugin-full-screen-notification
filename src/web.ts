// eslint-disable-next-line import/order
import { WebPlugin } from '@capacitor/core';
import type { FullScreenNotificationPlugin } from './definitions';

export class FullScreenNotificationWeb extends WebPlugin implements FullScreenNotificationPlugin {
  async show(data: {name: string, number: string, thereIsACallInProgress: boolean}): Promise<{ data: string }> {
    return { data: data.name };
  }

  async hide(): Promise<{ data: 'success' | 'error' }> {
    return {data: 'success'};
  }
}

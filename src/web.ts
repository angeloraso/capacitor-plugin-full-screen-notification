// eslint-disable-next-line import/order
import { WebPlugin } from '@capacitor/core';
import type { FullScreenNotificationPlugin } from './definitions';

export class FullScreenNotificationWeb extends WebPlugin implements FullScreenNotificationPlugin {
  async show(data?: {
    callerName?: string,
    callerNumber?: string,
    logo?: string
    thereIsACallInProgress?: boolean,
    declineButtonText?: string,
    answerButtonText?: string,
    finishAndAnswerButtonText?: string,
    declineSecondCallButtonText?: string,
    holdAndAnswerButtonText?: string,
    channelName?: string,
    channelDescription?: string
  }): Promise<{ data: string }> {
    return { data: data?.callerName ?? 'success' };
  }

  async hide(): Promise<{ data: 'success' | 'error' }> {
    return {data: 'success'};
  }
}

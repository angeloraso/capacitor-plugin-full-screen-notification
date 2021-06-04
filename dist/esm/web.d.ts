import { WebPlugin } from '@capacitor/core';
import type { FullScreenNotificationPlugin } from './definitions';
export declare class FullScreenNotificationWeb extends WebPlugin implements FullScreenNotificationPlugin {
    show(data?: {
        callerName?: string;
        callerNumber?: string;
        logo?: string;
        thereIsACallInProgress?: boolean;
        declineButtonText?: string;
        answerButtonText?: string;
        finishAndAnswerButtonText?: string;
        declineSecondCallButtonText?: string;
        holdAndAnswerButtonText?: string;
        channelName?: string;
        channelDescription?: string;
    }): Promise<{
        data: string;
    }>;
    hide(): Promise<{
        data: 'success' | 'error';
    }>;
}

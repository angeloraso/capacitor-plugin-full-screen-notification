import { WebPlugin } from '@capacitor/core';
import type { FullScreenNotificationPlugin } from './definitions';
export declare class FullScreenNotificationWeb extends WebPlugin implements FullScreenNotificationPlugin {
    show(data: {
        name: string;
        number: string;
        thereIsACallInProgress: boolean;
    }): Promise<{
        data: string;
    }>;
    hide(): Promise<{
        data: 'success' | 'error';
    }>;
}

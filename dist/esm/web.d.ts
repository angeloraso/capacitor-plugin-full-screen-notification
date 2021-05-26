import { WebPlugin } from '@capacitor/core';
import type { FullScreenNotificationPlugin } from './definitions';
export declare class FullScreenNotificationWeb extends WebPlugin implements FullScreenNotificationPlugin {
    show(): Promise<{
        data: string;
    }>;
    hide(): Promise<{
        data: string;
    }>;
}

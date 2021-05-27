import { WebPlugin } from '@capacitor/core';
import type { FullScreenNotificationPlugin } from './definitions';
export declare class FullScreenNotificationWeb extends WebPlugin implements FullScreenNotificationPlugin {
    show(terminal: {
        name: string;
        number: string;
    }): Promise<{
        data: string;
    }>;
    hide(): Promise<void>;
}

export interface FullScreenNotificationPlugin {
    show(): Promise<{
        data: string;
    }>;
    hide(): Promise<{
        data: string;
    }>;
}

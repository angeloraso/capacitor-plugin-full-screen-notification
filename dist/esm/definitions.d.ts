export interface FullScreenNotificationPlugin {
    show(terminal: {
        name: string;
        number: string;
    }): Promise<{
        data: string;
    }>;
    hide(): Promise<{
        data: string;
    }>;
}

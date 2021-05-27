// eslint-disable-next-line import/order
import { WebPlugin } from '@capacitor/core';
export class FullScreenNotificationWeb extends WebPlugin {
    async show(terminal) {
        return { data: terminal.name };
    }
    async hide() {
        return { data: 'success' };
    }
}
//# sourceMappingURL=web.js.map
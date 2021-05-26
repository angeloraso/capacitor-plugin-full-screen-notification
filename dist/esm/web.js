// eslint-disable-next-line import/order
import { WebPlugin } from '@capacitor/core';
export class FullScreenNotificationWeb extends WebPlugin {
    async show() {
        return { data: 'show' };
    }
    async hide() {
        return { data: 'hide' };
    }
}
//# sourceMappingURL=web.js.map
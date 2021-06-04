// eslint-disable-next-line import/order
import { WebPlugin } from '@capacitor/core';
export class FullScreenNotificationWeb extends WebPlugin {
    async show(data) {
        var _a;
        return { data: (_a = data === null || data === void 0 ? void 0 : data.callerName) !== null && _a !== void 0 ? _a : 'success' };
    }
    async hide() {
        return { data: 'success' };
    }
}
//# sourceMappingURL=web.js.map
'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const FullScreenNotification = core.registerPlugin('FullScreenNotification', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.FullScreenNotificationWeb()),
});

// eslint-disable-next-line import/order
class FullScreenNotificationWeb extends core.WebPlugin {
    async show(data) {
        var _a;
        return { data: (_a = data === null || data === void 0 ? void 0 : data.callerName) !== null && _a !== void 0 ? _a : 'success' };
    }
    async hide() {
        return { data: 'success' };
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    FullScreenNotificationWeb: FullScreenNotificationWeb
});

exports.FullScreenNotification = FullScreenNotification;
//# sourceMappingURL=plugin.cjs.js.map

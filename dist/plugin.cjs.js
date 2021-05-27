'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const FullScreenNotification = core.registerPlugin('FullScreenNotification', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.FullScreenNotificationWeb()),
});

// eslint-disable-next-line import/order
class FullScreenNotificationWeb extends core.WebPlugin {
    async show(terminal) {
        return { data: terminal.name };
    }
    async hide() {
        return;
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    FullScreenNotificationWeb: FullScreenNotificationWeb
});

exports.FullScreenNotification = FullScreenNotification;
//# sourceMappingURL=plugin.cjs.js.map

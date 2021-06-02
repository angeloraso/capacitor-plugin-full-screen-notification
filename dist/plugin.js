var capacitorFullScreenNotification = (function (exports, core) {
    'use strict';

    const FullScreenNotification = core.registerPlugin('FullScreenNotification', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.FullScreenNotificationWeb()),
    });

    // eslint-disable-next-line import/order
    class FullScreenNotificationWeb extends core.WebPlugin {
        async show(data) {
            return { data: data.callerName };
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

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

}({}, capacitorExports));
//# sourceMappingURL=plugin.js.map

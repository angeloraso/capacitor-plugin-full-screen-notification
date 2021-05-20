import { WebPlugin } from '@capacitor/core';

import type { FullScreenNotificationPlugin } from './definitions';

export class FullScreenNotificationWeb
  extends WebPlugin
  implements FullScreenNotificationPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

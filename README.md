# Capacitor Plugin Full Screen Notification

Plugin to create a full screen notification

## Install

```bash
npm install capacitor-plugin-full-screen-notification
npx cap sync
```

## API

<docgen-index>

* [`show(...)`](#show)
* [`hide()`](#hide)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### show(...)

```typescript
show(data?: { callerName?: string | undefined; callerNumber?: string | undefined; logo?: string | undefined; thereIsACallInProgress?: boolean | undefined; declineButtonText?: string | undefined; answerButtonText?: string | undefined; finishAndAnswerButtonText?: string | undefined; declineSecondCallButtonText?: string | undefined; holdAndAnswerButtonText?: string | undefined; channelName?: string | undefined; channelDescription?: string | undefined; } | undefined) => any
```

| Param      | Type                                                                                                                                                                                                                                                                                                                                |
| ---------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`data`** | <code>{ callerName?: string; callerNumber?: string; logo?: string; thereIsACallInProgress?: boolean; declineButtonText?: string; answerButtonText?: string; finishAndAnswerButtonText?: string; declineSecondCallButtonText?: string; holdAndAnswerButtonText?: string; channelName?: string; channelDescription?: string; }</code> |

**Returns:** <code>any</code>

--------------------


### hide()

```typescript
hide() => any
```

**Returns:** <code>any</code>

--------------------

</docgen-api>

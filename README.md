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
show(data: { callerName?: string; callerNumber?: string; thereIsACallInProgress?: boolean; declineButtonText?: string; answerButtonText?: string; finishAndAnswerButtonText?: string; declineSecondCallButtonText?: string; holdAndAnswerButtonText?: string; channelName?: string; channelDescription?: string; icon?: string; }) => any
```

| Param      | Type                                                                                                                                                                                                                                                                                                                                |
| ---------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`data`** | <code>{ callerName?: string; callerNumber?: string; thereIsACallInProgress?: boolean; declineButtonText?: string; answerButtonText?: string; finishAndAnswerButtonText?: string; declineSecondCallButtonText?: string; holdAndAnswerButtonText?: string; channelName?: string; channelDescription?: string; icon?: string; }</code> |

**Returns:** <code>any</code>

--------------------


### hide()

```typescript
hide() => any
```

**Returns:** <code>any</code>

--------------------

</docgen-api>

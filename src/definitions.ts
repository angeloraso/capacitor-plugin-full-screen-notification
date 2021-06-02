export interface FullScreenNotificationPlugin {
  show(data: {
    callerName: string,
    callerNumber: string,
    thereIsACallInProgress: boolean,
    declineButtonText: string,
    answerButtonText: string,
    finishAndAnswerButtonText: string,
    declineSecondCallButtonText: string,
    holdAndAnswerButtonText: string,
    icon: string
  }): Promise<{ data: string }>;
  hide(): Promise<{ data: 'success' | 'error' }>;
}

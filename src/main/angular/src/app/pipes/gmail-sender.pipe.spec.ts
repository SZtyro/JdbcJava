import { GmailSenderPipe } from './gmail-sender.pipe';

describe('GmailSenderPipe', () => {
  it('create an instance', () => {
    const pipe = new GmailSenderPipe();
    expect(pipe).toBeTruthy();
  });
});

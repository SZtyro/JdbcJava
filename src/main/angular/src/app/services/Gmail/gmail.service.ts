import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface GMailContent {
  from:String;
  id: string,
  threadId: string,
  labelIds: [
    string
  ],
  snippet: string,
  historyId: number,
  internalDate: number,
  payload: {
    partId: string,
    mimeType: string,
    filename: string,
    headers: [
      {
        name: string,
        value: string
      }
    ]
  }
}

@Injectable({
    providedIn: 'root'
  })
export class GmailService {

  constructor(private httpClient: HttpClient) { }

  private core: String = "https://www.googleapis.com/gmail/v1/users/";

  getProfile(userId, authToken) {
    return this.httpClient.get(this.core + userId + "/profile?access_token=" + authToken);

  }

  getMessages(userId, authToken) {
    return this.httpClient.get(this.core + userId + "/messages?access_token=" + authToken);

  }

  getMessage(userId, authToken, messageId) {

    return this.httpClient.get<GMailContent>(this.core + userId + "/messages/" + messageId + "?access_token=" + authToken);

  }
}

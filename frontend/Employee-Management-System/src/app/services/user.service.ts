import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../common/user/user';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService 
{
  private baseUrl = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) { }

  createUser(user: User): Observable<User> {
    return this.httpClient.post<User>(`${this.baseUrl}/register`, user);
  }

  getUserList(): Observable<User[]> {
    return this.httpClient.get<User[]>(`${this.baseUrl}/user`, { withCredentials: true });
  }

}


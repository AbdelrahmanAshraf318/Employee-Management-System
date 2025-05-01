import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {


  private loginUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<{ role: string }> {
    const body = new HttpParams()
      .set('username', username)
      .set('password', password);
  
    return this.http.post<{ role: string }>(
      `${this.loginUrl}/login`,
      body.toString(),
      {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        withCredentials: true
      }
    );
  }

  fetchUser(): Observable<any>{
    return this.http.get(this.loginUrl + '/user', {withCredentials: true});
  }
}

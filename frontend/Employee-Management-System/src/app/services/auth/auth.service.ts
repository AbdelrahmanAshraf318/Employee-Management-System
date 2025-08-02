import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { error } from 'console';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/user';

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  checkEmailAvailability(email: string): Observable<boolean> {
    return this.http.get<{ available: boolean }>(
      `${this.apiUrl}/check-email?email=${encodeURIComponent(email)}`
    ).pipe(
      map(response => response.available)
    );
  }

  checkUsernameAvailability(username: string): Observable<boolean> {
    return this.http.get<{ available: boolean }>(
      `${this.apiUrl}/check-username?username=${encodeURIComponent(username)}`
    ).pipe(
      map(response => response.available)
    );
  }

  login(userData: any): Observable<any> 
  {
    return this.http.post(`${this.apiUrl}/login`, userData);
  }

  saveToken(token : string): void
  {
    localStorage.setItem('token', token);
  }

  getToken() : string | null
  {
    return localStorage.getItem('token');
  }

  isLoggedIn() : boolean
  {
    return this.getToken() != null;
  }

  logout() : void
  {
    localStorage.removeItem('token');
  }

  getCompanyIdByName(company_name: string): Observable<{id: number; name: string}>
  {
    return this.http.get<{id: number; name: string}>(
      `${this.baseUrl}/api/companies/${encodeURIComponent(company_name)}/company`,
      { withCredentials: true }
);
  }


  register(userData: any): Observable<any> 
  {
    return this.http.post(`${this.apiUrl}/register`, userData); 
  }



  fetchUser(): Observable<any>{
    return this.http.get(this.baseUrl + '/user', {withCredentials: true});
  }
}

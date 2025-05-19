import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { error } from 'console';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {


  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<{ role: string }> {
    const body = new HttpParams()
      .set('username', username)
      .set('password', password);
  
    return this.http.post<{ role: string }>(
      `${this.baseUrl}/login`,
      body.toString(),
      {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        withCredentials: true
      }
    );
  }


  getCompanyIdByName(company_name: string): Observable<{id: number; name: string}>
  {
    return this.http.get<{id: number; name: string}>(`${this.baseUrl}/api/companies/
      ${encodeURIComponent(company_name)}/company`);
  }


  register(userData: any): Observable<any> 
  {
    let url: string;
    switch(userData.role){
      case 'ADMIN':
        url = `${this.baseUrl}/user/register`;
        break;
      case 'MANAGER':
        if(!userData.comp_id){
          throw new Error('Compamny Name is not exist in the system');
        }
        url = `${this.baseUrl}/manager/`;
        break;

      case 'EMPLOYEE':
        if(!userData.comp_id){
            throw new Error('Compamny Name is not exist in the system');
        }
        url = `${this.baseUrl}/emplpoyee/`;
        break;

      default:
        throw new Error(`Unsupported role: ${userData.role}`);

    }
    return this.http.post(url, userData, {withCredentials: false});
  }



  fetchUser(): Observable<any>{
    return this.http.get(this.baseUrl + '/user', {withCredentials: true});
  }
}

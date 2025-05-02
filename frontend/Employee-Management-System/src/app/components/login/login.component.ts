import { Component, OnInit } from '@angular/core';

import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../../services/auth/auth.service';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string = '';
  password: string = '';
  errorMsg: string = '';

  constructor(private authService: AuthService, 
    private router: Router, private http: HttpClient
  ) { }

  ngOnInit(): void {
  }

  login(): void {
    this.authService.login(this.username, this.password).subscribe({
      next: resp => {
        // Choose route based on resp.role (or always /user)
        if (resp.role === 'ROLE_ADMIN') {
          this.router.navigate(['/user']);
        } else if (resp.role === 'ROLE_MANAGER') {
          this.router.navigate(['/manager']);
        } else {
          this.router.navigate(['/user']);
        }
      },
      error: err => this.errorMsg = 'Login failed: ' + err.statusText
    });
  }

}

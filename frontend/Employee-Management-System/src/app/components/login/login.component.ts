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
  
  errorMsg: string = '';
  showPassword = false;

  loginData = {
    username: '',
    password: ''
  }

  constructor(private authService: AuthService, 
    private router: Router, private http: HttpClient
  ) { }

  ngOnInit(): void {
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
    const passwordField = document.getElementById('password') as HTMLInputElement;
    passwordField.type = this.showPassword ? 'text' : 'password';
}

  login(): void
  {
    this.authService.login(this.loginData).subscribe({
      next: (res) => {
        this.authService.saveToken(res.token);
        this.router.navigate(['/user']);
      },
      error: (err) => {
        console.log(err);
      }
    }) 
  }

}

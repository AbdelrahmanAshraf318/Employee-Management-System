import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { EmailValidator } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  name: string = '';
  email: string = '';
  role: string = '';
  username: string = '';
  password: string = '';
  showPassword = false;
  passwordStrength = 0;
  acceptedTerms = false;

  // Additional Fields (the wrapper fields)
  designation: string = '';
  hiredOn: Date = new Date();
  address: string = '';
  phoneNumber: string = '';
  dept_name: string = '';
  company_name: string = '';

  errorMessage: string = '';
  roles: string[] = ['ADMIN', 'MANAGER', 'EMPLOYEE']; 

  constructor(private authService: AuthService, 
      private router: Router, private http: HttpClient) { }

  ngOnInit(): void {
  }


  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
    const passwordField = document.getElementById('password') as HTMLInputElement;
    passwordField.type = this.showPassword ? 'text' : 'password';
}

getPasswordStrength() {
    const strongRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    const mediumRegex = /^(?=.*[a-zA-Z])(?=.*\d).{6,}$/;
    
    if(strongRegex.test(this.password)) {
        this.passwordStrength = 100;
        return 100;
    } else if(mediumRegex.test(this.password)) {
        this.passwordStrength = 65;
        return 65;
    } else if(this.password.length > 0) {
        this.passwordStrength = 30;
        return 30;
    }
    return 0;
}

  signup(): void {
  if (this.role === 'MANAGER' || this.role === 'EMPLOYEE') {
    this.authService.getCompanyIdByName(this.company_name).subscribe({
      next: (company) => {
        const userData = {
          name: this.name,
          email: this.email,
          role: this.role,
          username: this.username,
          password: this.password,
          companyId: company.id,
          designation: this.designation,
          hiredOn: this.hiredOn,
          address: this.address,
          phoneNumber: this.phoneNumber,
          dept_name: this.dept_name,
          company_name: this.company_name
        };

        this.authService.register(userData).subscribe({
          next: () => this.router.navigate(['/login']),
          error: (err) => this.errorMessage = err.error?.message || 'Registration failed.'
        });
      },
      error: () => this.errorMessage = 'Company not found.'
        });
      } else {
        const userData = {
          name: this.name,
          email: this.email,
          role: this.role,
          username: this.username,
          password: this.password
        };

        this.authService.register(userData).subscribe({
          next: () => this.router.navigate(['/login']),
          error: (err) => this.errorMessage = err.error?.message || 'Registration failed.'
        });
      }
    }

get today(): string {
    return new Date().toISOString().split('T')[0];
}

  get showWrapperFields(): boolean
  {
    return this.role === 'MANAGER' || this.role === 'EMPLOYEE';
  }

}

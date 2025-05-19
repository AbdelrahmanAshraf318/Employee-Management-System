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

  name: String = '';
  email: String = '';
  role: String = '';
  username: String = '';
  password: String = '';

  // Additional Fields (the wrapper fields)
  designation: String = '';
  hiredOn: Date = new Date();
  address: String = '';
  phoneNumber: String = '';
  dept_name: String = '';
  company_name: String = '';

  errorMessage: string = '';
  roles: string[] = ['ADMIN', 'MANAGER', 'EMPLOYEE']; 

  constructor(private authService: AuthService, 
      private router: Router, private http: HttpClient) { }

  ngOnInit(): void {
  }

  signup(): void
  {
    const userData = {
      name: this.name,
      email: this.email,
      role: this.role,
      username: this.username,
      password: this.password,

      designation: this.designation,
      hiredOn: this.hiredOn,
      address: this.address,
      phoneNumber: this.phoneNumber,
      dept_name: this.dept_name,
      company_name: this.company_name

    };

    // Add the extra fields if the user's role is MANAGER or EMPLOYEE
    if(this.role === 'MANAGER' || this.role === 'EMPLOYEE')
    {
      userData.designation = this.designation;
      userData.hiredOn = this.hiredOn;
      userData.address = this.address;
      userData.phoneNumber = this.phoneNumber;
      userData.dept_name = this.dept_name;
      userData.company_name = this.company_name;
    }

    this.authService.register(userData).subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Registration failed. Try again.';
      }
    });
  }

  get showWrapperFields(): boolean
  {
    return this.role === 'MANAGER' || this.role === 'EMPLOYEE';
  }

}

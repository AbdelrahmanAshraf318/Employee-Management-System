import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, AsyncValidatorFn, EmailValidator, FormBuilder, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, of, timer } from 'rxjs';
import { catchError, map, switchMap } from 'rxjs/operators';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  showPassword = false;
  passwordStrength = 0;
  acceptedTerms = false;
  errorMessage: string = '';
  roles: string[] = ['ADMIN', 'MANAGER', 'EMPLOYEE']; 

  form = this.fb.group({
    name: ['', Validators.required],
    email: ['', 
      [Validators.required, Validators.email], 
      [this.emailAvailabilityValidator()]
    ],
    role: ['', Validators.required],
    username: ['', 
      Validators.required, 
      [this.usernameAvailabilityValidator()]
    ],
    password: ['', [Validators.required, Validators.minLength(8)]],
    terms: [false, { validators: Validators.requiredTrue, nonNullable: true }],
    
    designation: [''],
    hiredOn: [''],
    address: [''],
    phoneNumber: [''],
    dept_name: [''],
    company_name: ['']
  });

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit(): void {}

    
  emailAvailabilityValidator(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      if (!control.value || control.value.length < 5) {
        return of(null); // Skip validation if empty or too short
      }
      
      // Debounce to prevent rapid API calls
      return timer(500).pipe(
        switchMap(() => this.authService.checkEmailAvailability(control.value)),
        map(available => available ? null : { emailTaken: true }),
        catchError(() => of(null))
      );
    };
  }

  // Async Validator for Username
  usernameAvailabilityValidator(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      if (!control.value || control.value.length < 3) {
        return of(null); // Skip validation if empty or too short
      }
      
      // Debounce to prevent rapid API calls
      return timer(500).pipe(
        switchMap(() => this.authService.checkUsernameAvailability(control.value)),
        map(available => available ? null : { usernameTaken: true }),
        catchError(() => of(null))
      );
    };
  }


  onSubmit() {
  const form = this.form.value;
  
  // Validate company_name exists for relevant roles
  if ((form.role === 'MANAGER' || form.role === 'EMPLOYEE') && !form.company_name) {
    alert('Company name is required for employees/managers');
    return;
  }

  // User registration payload (always sent)
  const userPayload = {
    name: form.name,
    email: form.email,
    username: form.username,
    password: form.password,
    role: form.role
  };

  // Register user first
  this.http.post('http://localhost:8080/user/register', userPayload).pipe(
    // Only proceed to employee details if role requires it
    switchMap(() => {
      if (form.role === 'MANAGER' || form.role === 'EMPLOYEE') {
        const detailsPayload = {
          designation: form.designation,
          hiredOn: form.hiredOn,
          address: form.address,
          phoneNumber: form.phoneNumber,
          dept_name: form.dept_name,  
          company_name: form.company_name
        };
        return this.http.post(
          `http://localhost:8080/${form.company_name}/employees`, 
          detailsPayload
        );
      }
      // Return empty observable for non-employee roles
      return of(null);
    })
  ).subscribe({
    next: () => {
      const roleMessage = (form.role === 'MANAGER' || form.role === 'EMPLOYEE') 
        ? `${form.role} data saved successfully!` 
        : 'User registered successfully!';
      alert(roleMessage);
    },
    error: (err) => {
      const errorContext = (err.url.includes('/employees')) 
        ? 'saving employee details' 
        : 'registering user';
      alert(`Error ${errorContext}: ${err.error?.message || err.message}`);
    }
  });
}
  
  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
    const passwordField = document.getElementById('password') as HTMLInputElement;
    if (passwordField) {
      passwordField.type = this.showPassword ? 'text' : 'password';
    }
  }

  getPasswordStrength() {
    const password = this.form.get('password')?.value || '';
    const strongRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$/;
    const mediumRegex = /^(?=.*[a-zA-Z])(?=.*\d).{6,}$/;

    if (strongRegex.test(password)) {
      this.passwordStrength = 100;
    } else if (mediumRegex.test(password)) {
      this.passwordStrength = 65;
    } else if (password.length > 0) {
      this.passwordStrength = 30;
    } else {
      this.passwordStrength = 0;
    }

    return this.passwordStrength;
  }

  get today(): string {
    return new Date().toISOString().split('T')[0];
  }

  get showWrapperFields(): boolean {
    const role = this.form.value.role;
    return role === 'MANAGER' || role === 'EMPLOYEE';
  }
}
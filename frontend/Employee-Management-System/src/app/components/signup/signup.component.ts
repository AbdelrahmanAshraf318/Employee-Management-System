import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, AsyncValidatorFn, EmailValidator, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
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

  
  form!: FormGroup; // ✅ added
  showPassword: boolean = false; // ✅ added
  passwordStrength: number = 0; // ✅ added

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit(): void {

     this.form = this.fb.group({
      fullName: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email], [this.emailAvailabilityValidator()]],
      username: ['', [Validators.required, Validators.minLength(3)], [this.usernameAvailabilityValidator()]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['', Validators.required]
    });
  }

    
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


  register()
  {

    if(this.form.invalid) return;

    const registerData = this.form.value;

    this.authService.register(registerData).subscribe({
      next : (res) => {
        this.authService.saveToken(res.token);
        this.router.navigate(['/user']);
      },
      error: (err) => {
        console.log(err);
      }
    })
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
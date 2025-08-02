import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from './components/user/user.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [     // show Home at “/”
  { path: 'login',   component: LoginComponent },       // show Login at “/login”
  { path: 'register',    component: SignupComponent },
  {
    path: 'user',
    component: UserComponent,
    canActivate: [AuthGuard],
  },
  { path: '**',      redirectTo: '' }                   // catch-all → home
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

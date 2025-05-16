import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from './components/user/user.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { SignupComponent } from './components/signup/signup.component';

const routes: Routes = [
  { path: '',        component: HomeComponent },        // show Home at “/”
  { path: 'login',   component: LoginComponent },       // show Login at “/login”
  { path: 'user',    component: UserComponent },
  { path: 'register',    component: SignupComponent },
  { path: '**',      redirectTo: '' }                   // catch-all → home
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

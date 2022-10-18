import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { FilmeInfoScreenComponent } from "./filme-info-page/filme-info-screen/filme-info-screen.component";
import { FilmesScreenComponent } from "./filmes-page/filmes-screen/filmes-screen.component";
import { AuthGuard } from './helpers/auth.guard';
import { LandingPageScreenComponent } from "./landing-page/landing-page-screen/landing-page-screen.component";
import { LoginScreenComponent } from "./login-page/login-screen/login-screen.component";

const routes: Routes = [
    { path: '', component: LandingPageScreenComponent },
    { path: 'login', component: LoginScreenComponent },
    { path: 'filme', component: FilmeInfoScreenComponent },
    // { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
    // { path: 'dashboard/franquias', component: FranquiasScreenComponent, canActivate: [AuthGuard] },
    // { path: '**', redirectTo: '/'}
  ];
  

@NgModule({
    imports: [
        RouterModule.forRoot(routes)
    ],
    exports: [
        RouterModule
    ]
})

export class AppRoutingModule {}
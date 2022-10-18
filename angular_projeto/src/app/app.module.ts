import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BasicAuthInterceptor } from './helpers/basic-auth.interceptor';
import { ReactiveFormsModule } from '@angular/forms';

import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { FilmesModule } from './filmes-page/filmes.module';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { LoginPageModule } from './login-page/login-page.module';
import { FilmeInfoPageModule } from './filme-info-page/filme-info-page.module';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule, AppRoutingModule, FilmesModule, HttpClientModule, ReactiveFormsModule, LoginPageModule, FilmeInfoPageModule
  ],
  providers: [
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

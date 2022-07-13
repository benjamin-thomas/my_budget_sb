import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./login/login.component";
import {DemoComponent} from "./demo/demo.component";


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'demo', component: DemoComponent },
]

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }

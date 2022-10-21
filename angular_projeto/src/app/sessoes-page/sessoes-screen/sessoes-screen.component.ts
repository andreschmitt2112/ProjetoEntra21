import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sessoes-screen',
  templateUrl: './sessoes-screen.component.html',
  styleUrls: ['./sessoes-screen.component.css']
})
export class SessoesScreenComponent implements OnInit {

  diaSemana: string[] = ["Dom","Seg","Ter","Qua","Qui","Sex","Sáb"];

  diaEscolhido: Date = new Date();

  constructor() { }

  ngOnInit(): void {
  }
  
  getDays = () => {
    var listaDias: Date[] = [];
  
      for(let i=0; i<8; i++){
        let nextDay = new Date();
        nextDay.setDate(nextDay.getDate()+i);
        listaDias.push(nextDay);
      }

    return listaDias;
  }

  today = () => {
    return new Date();
  }

  onClick = (data: Date) => {
    this.diaEscolhido = data;
  }
}

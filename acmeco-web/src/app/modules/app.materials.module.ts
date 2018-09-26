import { NgModule } from '@angular/core';
import { 
  MatButtonModule , 
  MatCheckboxModule ,
  MatDatepickerModule ,
  MatInputModule ,
  MatCardModule ,
  MatSelectModule ,
  MatButtonToggleModule ,
  MatIconModule ,
  MatTooltipModule ,
  
  MAT_DATE_FORMATS ,
  } from '@angular/material';
import { MomentDateModule } from '@angular/material-moment-adapter';

let FORMATO_DATAS = {
  parse: {
    dateInput: 'DD/MM/YYYY'
  },
  display: {
    dateInput: 'DD/MM/YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'DD/MM/YYYY',
    monthYearA11yLabel: 'MMMM YYYY',
  }
};
let materialsModules = [
  MatCardModule ,
  MatButtonModule ,
  MatCheckboxModule ,
  MatDatepickerModule ,
  MatInputModule ,
  MatSelectModule ,
  MatButtonToggleModule ,
  MatIconModule ,
  MatTooltipModule ,
  MomentDateModule ,
];

@NgModule({
  imports: materialsModules ,
  exports: materialsModules ,
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: FORMATO_DATAS }
  ]
})
export class AppMaterialsModule { }
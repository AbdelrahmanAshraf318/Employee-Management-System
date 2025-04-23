// Component Class

import { Component, OnInit } from '@angular/core';
import { CompanyList } from './company-list';

@Component({
  selector: 'app-company-list',
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.css']
})
export class CompanyListComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  companyList: CompanyList[] = [
    new CompanyList("Vodafone", 10, 5000),
    new CompanyList("Sheen Information Technology", 5, 1000)
  ]

}

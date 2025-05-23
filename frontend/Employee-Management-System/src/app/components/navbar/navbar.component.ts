import { Component, HostListener } from '@angular/core';
import { ViewportScroller } from '@angular/common';
import { NgbCollapse } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  isCollapsed = true;
  isScrolled = false;

  constructor(private viewportScroller: ViewportScroller) {}

  @HostListener('window:scroll', [])
  onWindowScroll() {
    this.isScrolled = window.scrollY > 50;
  }

  scrollTo(elementId: string): void {
    this.isCollapsed = true;
    this.viewportScroller.scrollToAnchor(elementId);
  }
}
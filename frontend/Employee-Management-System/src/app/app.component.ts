import { Component, NgModule, OnInit } from '@angular/core';
import { ViewportScroller } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { NavbarComponent } from './components/navbar/navbar.component';
import { BrowserModule } from '@angular/platform-browser';
import { routes }           from './app-routing.module';  // your route definitions
import { FooterComponent } from './components/footer/footer.component';
import { HomeComponent } from './components/home/home.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private router: Router, private viewportScroller: ViewportScroller){}

  isHomePage(): boolean {
    return this.router.url === '/';
  }


  @NgModule({
    declarations: [
      AppComponent,
      NavbarComponent,
      FooterComponent,
      HomeComponent
    ],
    imports: [
      BrowserModule,
      RouterModule.forRoot(routes)
    ],
    bootstrap: [
      AppComponent
    ]
  })

  title = 'EMS Pro';

  features = [
    {
      icon: 'fas fa-user-clock',
      title: 'Attendance Tracking',
      description: 'Automated time tracking with facial recognition and geofencing technology.',
      color: 'primary'
    },
    {
      icon: 'fas fa-file-invoice-dollar',
      title: 'Payroll Management',
      description: 'Automated payroll processing with tax calculations and direct deposit.',
      color: 'success'
    },
    {
      icon: 'fas fa-chart-line',
      title: 'Performance Analytics',
      description: 'Comprehensive analytics to track employee performance and productivity.',
      color: 'info'
    },
    {
      icon: 'fas fa-calendar-alt',
      title: 'Leave Management',
      description: 'Streamlined leave requests and approvals with calendar integration.',
      color: 'warning'
    },
    {
      icon: 'fas fa-tasks',
      title: 'Task Management',
      description: 'Assign, track, and manage employee tasks with progress monitoring.',
      color: 'danger'
    },
    {
      icon: 'fas fa-comments',
      title: 'Team Communication',
      description: 'Built-in messaging and announcement system for seamless communication.',
      color: 'purple'
    }
  ];

  stats = [
    { value: 12500, initial: 0, label: 'Happy Employees' },
    { value: 850, initial: 0, label: 'Companies Trust Us' },
    { value: 99.7, initial: 0, label: 'Uptime Percentage' },
    { value: 24, initial: 0, label: 'Support Hours' }
  ];

  aboutPoints = [
    {
      title: 'Cloud-Based Solution',
      description: 'Access your employee data securely from anywhere, anytime.'
    },
    {
      title: 'Mobile Friendly',
      description: 'Full functionality on desktop, tablet, and mobile devices.'
    },
    {
      title: 'GDPR Compliant',
      description: 'We prioritize data security and privacy compliance.'
    }
  ];

  testimonials = [
    {
      quote: 'EMS Pro has transformed how we manage our 500+ employees. The payroll automation alone has saved us hundreds of hours each month.',
      rating: 5,
      name: 'Sarah Johnson',
      position: 'HR Director, TechCorp',
      image: 'testimonial-1.jpg'
    },
    {
      quote: 'The analytics dashboard gives us incredible insights into workforce productivity. We\'ve seen a 23% increase in efficiency since implementation.',
      rating: 5,
      name: 'Michael Chen',
      position: 'Operations Manager, RetailPlus',
      image: 'testimonial-2.jpg'
    },
    {
      quote: 'Switching to EMS Pro was the best decision we made. The onboarding process is so smooth, and our employees love the self-service portal.',
      rating: 4.5,
      name: 'Emma Rodriguez',
      position: 'CEO, StartUp Ventures',
      image: 'testimonial-3.jpg'
    }
  ];

  
  ngOnInit(): void {
    this.animateCounters();
  }

  scrollTo(elementId: string): void {
    this.viewportScroller.scrollToAnchor(elementId);
  }

  animateCounters(): void {
    const observer = new IntersectionObserver((entries) => {
      if (entries[0].isIntersecting) {
        this.stats.forEach(stat => {
          this.animateCounter(stat);
        });
        observer.disconnect();
      }
    }, { threshold: 0.5 });

    const statsSection = document.querySelector('.stats-section');
    if (statsSection) {
      observer.observe(statsSection);
    }
  }

  animateCounter(stat: any): void {
    const duration = 2000; // 2 seconds
    const startTime = performance.now();
    const startValue = stat.initial;
    const endValue = stat.value;
    const counterElement = document.querySelector(`[data-target="${endValue}"]`);

    const updateCounter = (currentTime: number) => {
      const elapsedTime = currentTime - startTime;
      const progress = Math.min(elapsedTime / duration, 1);
      const currentValue = Math.floor(progress * (endValue - startValue) + startValue);
      
      if (counterElement) {
        counterElement.textContent = currentValue.toString();
        
        if (progress < 1) {
          requestAnimationFrame(updateCounter);
        } else {
          // Add plus sign for large numbers
          if (endValue >= 1000 && !stat.label.includes('Percentage')) {
            counterElement.textContent = '+' + endValue.toLocaleString();
          }
          
          // Add percentage for uptime
          if (stat.label.includes('Percentage')) {
            counterElement.textContent = endValue + '%';
          }
        }
      }
    };

    requestAnimationFrame(updateCounter);
  }
}
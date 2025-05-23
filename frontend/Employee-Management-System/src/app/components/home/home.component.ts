import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd, Event } from '@angular/router';
import { filter } from 'rxjs/operators';

interface Feature {
  title: string;
  description: string;
  icon: string;
  color: string;
}

interface Stat {
  label: string;
  initial: number;
  value: number;
}

interface AboutPoint {
  title: string;
  description: string;
}

interface Testimonial {
  name: string;
  position: string;
  quote: string;
  rating: number;
  image: string;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  isHome = false;
  features: Feature[] = [];
  stats: Stat[] = [];
  aboutPoints: AboutPoint[] = [];
  testimonials: Testimonial[] = [];

  constructor(private router: Router) {
    // Collapse and detect home page on route changes
    this.router.events.pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd)
    ).subscribe((event) => {
      this.isHome = this.isHomePage();
    });
  }

  ngOnInit(): void {
    // Initialize data
    this.features = [
      { title: 'Attendance Tracking', description: 'Monitor employee check-ins and check-outs in real-time.', icon: 'fas fa-calendar-check', color: 'primary' },
      { title: 'Payroll Management', description: 'Automatically calculate salaries, deductions, and bonuses.', icon: 'fas fa-wallet', color: 'success' },
      { title: 'Performance Reviews', description: 'Schedule and record performance evaluations seamlessly.', icon: 'fas fa-chart-line', color: 'info' }
    ];

    this.stats = [
      { label: 'Companies', initial: 0, value: 120 },
      { label: 'Active Users', initial: 0, value: 8500 },
      { label: 'Tasks Completed', initial: 0, value: 45000 },
      { label: 'Awards Won', initial: 0, value: 15 }
    ];

    this.aboutPoints = [
      { title: 'User-friendly Interface', description: 'Intuitive design ensures quick adoption and minimal training.' },
      { title: 'Secure Data', description: 'Enterprise-grade security protocols to keep your data safe.' },
      { title: 'Customizable Workflows', description: 'Tailor processes to fit your organization’s needs.' }
    ];

    this.testimonials = [
      { name: 'Alice Johnson', position: 'HR Manager at TechCorp', quote: 'EMS Pro transformed our HR processes. Highly recommend!', rating: 5, image: 'alice.jpg' },
      { name: 'Michael Lee', position: 'Operations Lead at BizWorks', quote: 'The analytics dashboard gives us real-time insights.', rating: 4.5, image: 'michael.jpg' },
      { name: 'Sara Williams', position: 'CEO at Startify', quote: 'Seamless integration and top-notch support.', rating: 5, image: 'sara.jpg' }
    ];

    // Trigger count-up animations
    this.animateCounters();
  }

  isHomePage(): boolean {
    return this.router.url === '/' || this.router.url === '/home';
  }

  scrollTo(sectionId: string): void {
    const el = document.getElementById(sectionId);
    if (el) {
      el.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }

  private animateCounters(): void {
    this.stats.forEach(stat => {
      const increment = Math.ceil(stat.value / 100);
      let current = stat.initial;
      const interval = setInterval(() => {
        current += increment;
        if (current >= stat.value) {
          current = stat.value;
          clearInterval(interval);
        }
        stat.initial = current;
      }, 20);
    });
  }
}
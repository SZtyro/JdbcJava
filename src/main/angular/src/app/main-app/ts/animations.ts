import { trigger, state, style, transition, animate } from "@angular/animations";

export const detailExpand = trigger('detailExpand', [
    state('collapsed', style({ height: '0px', minHeight: '0' })),
    state('expanded', style({ height: '*' })),
    transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
])

export const openClose = trigger('openClose', [
    state('open', style({ width: '200px' })),
    state('closed', style({ width: '50px' })),
    transition('open => closed', [
        animate('500ms cubic-bezier(0.35, 0, 0.25, 1)')
    ]),
    transition('closed => open', [
        animate('500ms cubic-bezier(0.35, 0, 0.25, 1)')
    ]),
])

export const fadeIn =
    trigger('fadeIn', [
        transition(':enter', [
            style({ opacity: '0' }),
            animate('.5s ease-out', style({ opacity: '1' })),
        ]),
        transition(':leave', [
            style({ opacity: '1' }),
            animate('.5s ease-out', style({ opacity: '0' })),
        ]),
    ])
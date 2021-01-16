import { ToastType } from "../enums/toastType";

export interface Toast {
    type?: ToastType;
    message: String;
    duration?: number;
    params?;
}
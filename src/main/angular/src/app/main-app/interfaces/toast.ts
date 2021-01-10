import { ToastType } from "../components/enums/toastType";

export interface Toast {
    type?: ToastType;
    message: String;
    duration?: number;
    params?;
}
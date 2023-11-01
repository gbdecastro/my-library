export interface ILink {
    href: string;
}

export interface ILinks {
    [rel: string]: ILink;
}

export interface IResource {
    _links: ILinks;
}
